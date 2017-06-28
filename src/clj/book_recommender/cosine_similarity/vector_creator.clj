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
  [bookAttribute mainBook refBook data]
  (* (tfidf/calculate-tf (get refBook bookAttribute) (get mainBook bookAttribute))
     (tfidf/calculate-idf (get refBook bookAttribute) (extract-all-values-for-attribute bookAttribute data))))


(defn create-book-vector
  "Creates b tf/idf valued book vector"
  [mainBook refBook data]
  (map #(* (calculate-tfidf % mainBook refBook data)) [:author_name :author_movement :genre]))

