(ns book-recommender.test.cosine-similarity.vector-creator-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [book-recommender.cosine-similarity.vector-creator :refer :all]
            [book-recommender.reader.csv-reader :refer :all]))


(deftest test-extracting-attributes-array
  (testing "extraction of array of certain attributes for whole dataset")

  (let [data (load-data-from-file "C:\\dev\\projects\\book-recommender\\test\\resource\\testBookDataSet.csv")
        expectedData ["Dostojevski", "Dostojevski", "Dostojevski", "Tolstoj", "Bajron", "Tolkin"]]

    (is (= expectedData (extract-all-values-for-attribute :author_name data)))))

(deftest test-calculate-tfidf
  (testing "calculation of tf/idf value for ref book for given book attribute")

  (let [data (load-data-from-file "C:\\dev\\projects\\book-recommender\\test\\resource\\testBookDataSet.csv")
        mainBook (first data)
        reffBook (second data)
        expectedTfIdfValue 0.3010299956639812
        expextedBookVector [0.3010299956639812 0.0 0.0]]

    (is (= expectedTfIdfValue (calculate-tfidf :author_name mainBook reffBook data)))
    (is (= expextedBookVector (create-book-vector mainBook reffBook data)))))

