(ns book-recommender.routes.home
  (:require [book-recommender.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [compojure.core :refer [defroutes POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [book-recommender.db.db :as db]
            [book-recommender.validator.validator :as validator]
            [ring.middleware.session :as session]
            [ring.util.response :as res]
            [book-recommender.finder.book-finder :as book-finder]
            [book-recommender.engine.book-recommender-engine :as engine]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page [username]
  (layout/render "about.html" {:docs (-> "README.md" io/resource slurp) :logged-in-user (db/search-for-user username)}))

(defn login-page
  ([] (layout/render "login.html"))
  ([error-message]
   (layout/render
     "login.html"
     {:error-message error-message})))

(defn register-page
  ([]
  (layout/render "register.html"))
  ([error-message]
    (layout/render "register.html" {:error-message error-message})))

(defn dashboard-page
  ([user]
  (layout/render "dashboard.html" {:logged-in-user user}))
  ([user books]
   (println "********************" user)
   (layout/render "dashboard.html" {:logged-in-user user :found-books books})))

(defn handle-login [username password]
  (if (not= 0 (count (validator/validate-login-form username password)))
    (login-page (validator/validate-login-form username password))
    (let [user (db/search-for-user username)]
    (if (empty? user)
      (login-page "There is no user with provided username!")
      (if (not= (get-in user [:password]) password)
        (login-page "Given password doesn't match provided user!")
        (dashboard-page user))))))

(defn successfull-register [username password firstname lastname]
  (db/insert-user username password firstname lastname)
  (dashboard-page {:username username :password password :firstname firstname :lastname lastname}))

(defn handle-register [firstname lastname username password repeatpwd]
  (if (not= 0 (count (validator/validate-register-form username password firstname lastname repeatpwd)))
    (register-page (validator/validate-register-form username password firstname lastname repeatpwd))
    (let [user (db/search-for-user username)]
    (if (empty? user)
      (successfull-register username password firstname lastname)
      (register-page "User with provided username already exists. Try with different username.")))))

(defn handle-search-request [logged-in-user search-input]
  (let [found-books (book-finder/find-by-name search-input)]
    (dashboard-page (read-string logged-in-user) found-books)))

(defn recommendations-page [user recommended-books]
  (layout/render "recommendations.html" {:logged-in-user (read-string user) :recommended-books recommended-books}))

(defn handle-recommendation-request [book user num-recommendations]
  (db/save-searched-book book user)
  (recommendations-page user (engine/recommend-for-book book (read-string num-recommendations))))

(defn profile-page
  ([username]
  (layout/render "profile.html" {:logged-in-user (db/search-for-user username)}))
  ([username newpwd repeatedpwd]
    (db/update-password username newpwd)
    (login-page)))

(defn contact-page [username]
  (layout/render "contact.html" {:logged-in-user (db/search-for-user username)}))

(defn books-page [username]
  (layout/render "books.html" {:logged-in-user (db/search-for-user username)
                               :searched-books (db/get-books-searched-by-user username)}))

(defroutes home-routes
  (GET "/" [] (res/redirect "/login"))
  (GET "/about/:username" [username] (about-page username))
  (GET "/login" [] (login-page))
  (GET "/register" [] (register-page))
  (GET "/dashboard" [user] (dashboard-page user))
  (POST "/login" [username password]
    (handle-login username password))
  (POST "/register" [firstname lastname username password repeatpwd]
    (handle-register firstname lastname username password repeatpwd))
  (POST "/search" [logged-in-user search-input] (handle-search-request logged-in-user search-input))
  (POST "/recommend" [num-of-recommendations book user]
    (handle-recommendation-request book user num-of-recommendations))
  (GET "/profile/:username" [username] (profile-page username))
  (GET "/dashboard/:username" [username] (dashboard-page (db/search-for-user username)))
  (POST "/change-password" [username newpwd repeatedpwd] (profile-page username newpwd repeatedpwd))
  (GET "/contact/:username" [username] (contact-page username))
  (GET "/books/:username" [username] (books-page username)))

