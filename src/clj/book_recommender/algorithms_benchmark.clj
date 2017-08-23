(ns book-recommender.algorithms-benchmark
  (:require [criterium.core :refer :all]
            [book-recommender.cosine-similarity.cosine-similarity-calculator :refer :all]
            [book-recommender.reader.csv-reader :refer :all]
            [book-recommender.engine.book-recommender-engine :refer :all]
            [book-recommender.cosine-similarity.tfidf-calculator :refer :all]))

(defn benchmark-idf
  []
  (let [param "A"
        dataSet (vec (repeat 10000000 "A"))]
    (quick-bench (calculate-idf param dataSet))
    (quick-bench (calculate-idf-empty param dataSet))
    (quick-bench (calculate-idf-r param dataSet))))

(defn benchmark-idf-time
  []
  (let [param "A"
        dataSet (vec (repeat 10000000 "A"))]
    (println "**************")
    (println "Before core.reducers")
    (time (calculate-idf param dataSet))
    (println "-----------------")
    (println "After switching to empty?")
    (time (calculate-idf-empty param dataSet))
    (println "-----------------")
    (println "After core.reducers")
    (time (calculate-idf-r param dataSet))
    (println "**************")))

(defn benchmark-scalar-product
  []
  (let [vectorA (vec (repeat 10000000 3))
        vectorB (vec (repeat 10000000 2))]
    (quick-bench (calculate-scalar-product vectorA vectorB))
    (quick-bench (calculate-scalar-product-r vectorA vectorB))))

(defn benchmark-scalar-product-time
  []
  (let [vectorA (vec (repeat 10000000 3))
        vectorB (vec (repeat 10000000 2))]
    (println "**************")
    (println "Before core.reducers")
    (time (calculate-scalar-product vectorA vectorB))
    (println "-----------------")
    (println "After core.reducers")
    (time (calculate-scalar-product-r vectorA vectorB))
    (println "**************")))

(defn benchmark-vector-intensity
  []
  (let [vectorA (vec (repeat 10000000 3))]
    (quick-bench (calculate-intensity-of-vector vectorA))
    (quick-bench (calculate-intensity-of-vector-r vectorA))))

(defn benchmark-recommender
  []
  (let [data (load-data-from-file "C:\\dev\\projects\\book-recommender\\test\\resource\\testBookDataSet.csv")
        refBook (first data)]
    (quick-bench (recommend-books refBook 10 data))
    (quick-bench (recommend-books-r refBook 10 data))))

