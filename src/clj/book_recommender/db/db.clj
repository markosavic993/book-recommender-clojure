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

(defn search-for-book
  "finds book by its uri"
  [uri]
  (first (jdbc/query db-spec ["SELECT * FROM book WHERE uri = ?" uri])))

(defn update-password
  "updates password for user"
  [username newpwd]
  (jdbc/update! db-spec "user" {:password newpwd} ["username = ?" username]))

(defn insert-book
  "insert book into database"
  [book]
  (jdbc/insert! db-spec "book"
                {:uri (:uri book)
                 :name (:name book)
                 :author_name (:author_name book)
                 :author_movement (:author_movement book)
                 :genre (:genre book)}))

(defn insert-searched-book
  "insert user's search "
  [book user]
  (jdbc/insert! db-spec "searched_books"
                {:user (:username user)
                 :book (:uri book)}))


(defn save-searched-book
  "saves search input (book) in book table (if not exist) and add it to searched books anyway"
  [book user]
  (if (nil? (search-for-book (:uri (read-string book))))
    (insert-book (read-string book)))
  (insert-searched-book (read-string book) (read-string user)))

(defn get-books-searched-by-user
  "gets books searched by user with given username"
  [username]
  (into []
        (map #(search-for-book (:book %))
             (jdbc/query db-spec
                         ["SELECT * FROM searched_books WHERE user = ?" username]))))