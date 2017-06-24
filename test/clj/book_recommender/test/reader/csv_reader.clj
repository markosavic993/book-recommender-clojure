(ns book-recommender.test.reader.csv-reader
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [book-recommender.reader.csv-reader :refer :all]))

(deftest test-load-data
  (testing "loading csv data properly")
  (let [expectedData [{:prop1 "val11", :prop2 "val12", :prop3 "val13"}
                      {:prop1 "val21", :prop2 "val22", :prop3 "val23"}
                      {:prop1 "val31", :prop2 "val32", :prop3 "val33"}]
        loadedData (load-data-from-file "C:\\dev\\projects\\book-recommender\\test\\resource\\testData.csv")]

    (is (= expectedData loadedData))))
