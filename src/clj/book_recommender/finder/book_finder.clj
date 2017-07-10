(ns book-recommender.finder.book-finder
  (:require [book-recommender.reader.csv-reader :as reader]
            [medley.core :refer :all]))

(def path-to-data "resources/books.csv")

(def books (reader/load-data-from-file path-to-data))

(defn find-by-name
  "finds books by provided name"
  ([search-input]
  (distinct-by #(:uri %)
               (filter #(.contains
                          (.toUpperCase (get % :name))
                          (.toUpperCase search-input))
                       books)))
  ([search-input data]
   (distinct-by #(:uri %)
                (filter #(.contains
                           (.toUpperCase (get % :name))
                           (.toUpperCase search-input))
                        data))))
