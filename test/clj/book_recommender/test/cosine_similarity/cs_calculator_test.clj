(ns book-recommender.test.cosine-similarity.cs-calculator-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [book-recommender.cosine-similarity.cosine-similarity-calculator :refer :all]))

(deftest test-scalar-product
  (testing "calculation of scalar product of given vectors")
  (let [vectorA  [1 2 3]
        vectorB [3 2 1]]
    (is (= 10 (calculate-scalar-product vectorA vectorB)))))

(deftest test-vector-intensity
  (testing "calculation of intensity of given vector")
  (let [vectorA  [1 2 2]]
    (is (= 3.0 (calculate-intensity-of-vector vectorA)))))

(deftest test-cosine-similarity
  (testing "calculation of cosine similarity of given vectors")
  (let [vectorA  [1 2 3]
        vectorB [3 2 1]]
    (is (= 0.7142857142857143 (calculate-cosine-similarity vectorA vectorB)))))

(deftest test-division-by-zero
  (testing "handling division with zero")
  (let [vectorA [0 0 0]
        vectorB [1 1 1]]
    (is (= 0.0 (calculate-cosine-similarity vectorA vectorB)))))