(ns user
  (:require [mount.core :as mount]
            book-recommender.core))

(defn start []
  (mount/start-without #'book-recommender.core/repl-server))

(defn stop []
  (mount/stop-except #'book-recommender.core/repl-server))

(defn restart []
  (stop)
  (start))


