(ns book-recommender.cosine-similarity.tfidf-calculator
  (:require [clojure.set]
            [clojure.core.reducers :as r])
  (:import (java.security InvalidParameterException)))

(defn calculate-tf
      "returns double value that represents term frequency"
      [refAttributeValue currentAttributeValue]
      (double (if (= refAttributeValue currentAttributeValue)
            1
            0)))

(defn calculate-idf
      "returns double value that represents inverse document frequency"
      [mainSet dataSet]
      ;(println "idf main set" mainSet)
      (if (= 0 (count (filter #(= mainSet %) dataSet))) (throw (InvalidParameterException. "provided data entry is not from provided dataset")))
      (Math/log10 (/ (count dataSet)
                     (count (filter #(= mainSet %) dataSet)))))

(defn calculate-idf-empty
  "returns double value that represents inverse document frequency"
  [mainSet dataSet]
  ;(println "idf main set" mainSet)
  (if (empty? (filter #(= mainSet %) dataSet)) (throw (InvalidParameterException. "provided data entry is not from provided dataset")))
  (Math/log10 (/ (count dataSet)
                 (count (filter #(= mainSet %) dataSet)))))

(defn calculate-idf-r
  "returns double value that represents inverse document frequency"
  [mainSet dataSet]
  ;(println "idf main set" mainSet)
  (if (empty? (into [] (r/filter #(= mainSet %) dataSet))) (throw (InvalidParameterException. "provided data entry is not from provided dataset")))
  (Math/log10 (/ (count dataSet)
                 (count (into [] (r/filter #(= mainSet %) dataSet))))))