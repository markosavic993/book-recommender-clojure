(ns book-recommender.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[book-engine started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[book-engine has shut down successfully]=-"))
   :middleware identity})
