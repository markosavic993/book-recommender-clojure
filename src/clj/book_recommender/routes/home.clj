(ns book-recommender.routes.home
  (:require [book-recommender.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [compojure.core :refer [defroutes POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defn login-page []
  (layout/render "login.html"))

(defn register-page []
  (layout/render "register.html"))

(defn handle-login [username password]
  (layout/render "about.html")
  )

(defn handle-register [firstname lastname username password]
  (println firstname lastname username password)
  )

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/login" [] (login-page))
  (GET "/register" [] (register-page))
  (POST "/login" [username password]
    (handle-login username password))
  (POST "/register" [firstname lastname username password]
    (handle-register firstname lastname username password)))

