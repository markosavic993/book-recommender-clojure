(ns book-recommender.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [book-recommender.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[book-engine started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[book-engine has shut down successfully]=-"))
   :middleware wrap-dev})
