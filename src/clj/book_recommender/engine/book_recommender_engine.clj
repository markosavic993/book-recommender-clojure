(ns book-recommender.engine.book-recommender-engine
  (:require [book-recommender.cosine-similarity.vector-creator :as creator]
            [book-recommender.cosine-similarity.cosine-similarity-calculator :as calculator]
            [book-recommender.reader.csv-reader :as reader]
            [book-recommender.finder.book-finder :refer :all]
            [medley.core :refer :all]
            [clojure.core.reducers :as r]))

(def path-to-production-data "resources/books.csv")

(def book-data-set (reader/load-data-from-file path-to-production-data))

(defn recommend-books
  "recommends books for given ref book"
  [ref-book num-of-recommendations data]
  (let [ref-book-vector (creator/create-book-vector ref-book
                                                  ref-book
                                                  data)
        vectors (map #(creator/create-book-vector ref-book % data) data)
        valuedBooksVector (into [] (map #(calculator/calculate-cosine-similarity ref-book-vector %) vectors))
        book-similarity-map (sort-by :value > (remove #(= (:book %) ref-book) (map (fn [key value] {:book key :value value}) data valuedBooksVector)))]
        (into [] (take num-of-recommendations
                       (distinct-by #(:uri (:book %)) book-similarity-map)))))

(defn recommend-books-r
  "recommends books for given ref book"
  [ref-book num-of-recommendations data]
  (let [ref-book-vector (creator/create-book-vector ref-book
                                                    ref-book
                                                    data)
        vectors (r/map #(creator/create-book-vector ref-book % data) data)
        valuedBooksVector (into [] (r/map #(calculator/calculate-cosine-similarity-r ref-book-vector %) vectors))
        book-similarity-map (sort-by :value > (remove #(= (:book %) ref-book) (map (fn [key value] {:book key :value value}) data valuedBooksVector)))]
    (into [] (r/take num-of-recommendations
                   (distinct-by #(:uri (:book %)) book-similarity-map)))))

(defn recommend-for-book
  "recommends books for given input for production data"
  [ref-book num-of-recommendations]
  (into [] (r/map #(:book %) (recommend-books-r (read-string ref-book)
                   num-of-recommendations
                   book-data-set))))