(ns book-recommender.cosine-similarity.vector-creator
  (:require [book-recommender.cosine-similarity.tfidf-calculator :as tfidf]
            [book-recommender.reader.csv-reader :as bookReader]
            [clojure.string :as str]))

(defn extract-all-values-for-attribute
  "extract array of values for given attribute from data set"
  [bookAttribute data]
  (map #(get % bookAttribute)  data))

(defn calculate-tfidf
  "calculate tf/idf for referenced book for given book attribute"
  [book-attribute ref-book other-book data]
  (* (tfidf/calculate-tf (get other-book book-attribute) (get ref-book book-attribute))
     (tfidf/calculate-idf (get other-book book-attribute) (extract-all-values-for-attribute book-attribute data))))


(defn create-book-vector
  "Creates b tf/idf valued book vector"
  [ref-book other-book data]
  (map #(* (calculate-tfidf % ref-book other-book data)) [:author_name :author_movement :genre]))

