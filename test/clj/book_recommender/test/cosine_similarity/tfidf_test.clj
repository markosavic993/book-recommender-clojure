(ns book-recommender.test.cosine-similarity.tfidf-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [book-recommender.cosine-similarity.tfidf-calculator :refer :all])
  (:import (java.security InvalidParameterException)))

(deftest test-tf
  (testing "calculation of tf of given inputs")
  (let [firstParam "Dostoyevsky"
        matchingParam "Dostoyevsky"
        notMatchingParam "Tolkin"]
    (is (= 1.0 (calculate-tf firstParam matchingParam)))
    (is (= 0.0 (calculate-tf firstParam notMatchingParam)))))

(deftest test-idf
  (testing "calculation of idf of given inputs")
  (let [param "A"
        dataSet ["A" "A" "C" "B"]
        notMatchingDataSet ["C" "D"]]
    (is (= 0.3010299956639812 (calculate-idf param dataSet)))
    (is (thrown? InvalidParameterException (calculate-idf param notMatchingDataSet)))))