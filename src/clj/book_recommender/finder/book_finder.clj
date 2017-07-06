(ns book-recommender.finder.book-finder
  (:require [book-recommender.reader.csv-reader :as reader]))

(def path-to-data "resources/books.csv")

(def books (reader/load-data-from-file path-to-data))

(defn find-by-name
  "finds books by provided name"
  ([search-input]
  (filter
    #(.contains (.toUpperCase (get % :name))
                (.toUpperCase search-input))
    books))
  ([search-input data]
   (distinct (filter
     #(.contains (.toUpperCase (get % :name))
                 (.toUpperCase search-input))
     data))))
