(ns book-recommender.db.db
  (:require [clojure.java.jdbc :as jdbc]))

(def db-spec
  {:dbtype "mysql"
   :dbname "book_recommendation"
   :user "root"
   :password "password"})

(defn insert-user
  "insert user to the database"
  [username password firstname lastname]
  (jdbc/insert! db-spec "user"
                {:username username :password password :firstname firstname :lastname lastname}))

(defn get-all-users
  "gets all the users from db"
  []
  (jdbc/query db-spec ["SELECT * FROM user"]))

(defn search-for-user
  "finds user by its username"
  [username]
  (first (jdbc/query db-spec ["SELECT * FROM user WHERE username = ?" username])))