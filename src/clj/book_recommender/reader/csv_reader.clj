(ns book-recommender.reader.csv-reader
  (:require [semantic-csv.core :as sc]))

(defn load-data-from-file
  "loads data from csv file"
  [pathToFile]
  (sc/slurp-csv pathToFile))
