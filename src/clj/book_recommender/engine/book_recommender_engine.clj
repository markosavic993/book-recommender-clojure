(ns book-recommender.engine.book-recommender-engine
  (:require [book-recommender.cosine-similarity.vector-creator :as creator]
            [book-recommender.cosine-similarity.cosine-similarity-calculator :as calculator]
            [book-recommender.reader.csv-reader :as reader]))

(def path-to-production-data "C:\\dev\\projects\\book-recommender\\resources\\books.csv")

(def book-data-set (reader/load-data-from-file path-to-production-data))

(defn recommand-books
  "recommends books for given ref book"
  [refBook numOfRecommendations data]
  (let [refBookVector (creator/create-book-vector refBook refBook data)
        vectors (map #(creator/create-book-vector refBook % data) data)
        valuedBooksVector (into [] (map #(calculator/calculate-cosine-similarity refBookVector %) vectors))
        book-similarity-map (map (fn [key value] {key value}) data valuedBooksVector)]
        (into [] (take numOfRecommendations book-similarity-map))))

(defn recommend-for-book
  "recommends books for given input for production data"
  [refBook numOfRecommendations]
  (recommand-books refBook numOfRecommendations book-data-set))