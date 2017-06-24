(ns book-recommender.test.engine.book-recommedner-engine-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [book-recommender.engine.book-recommender-engine :refer :all]
            [book-recommender.reader.csv-reader :refer :all]))

(deftest test-recommender
  (testing "generating recommendations for given book")
  (let [data (load-data-from-file "C:\\dev\\projects\\book-recommender\\test\\resource\\testBookDataSet.csv")
        refBook (first data)
        expectedresult [{{:a "Dostojevski", :b "Drama", :c "srednji vek"}
                         0.9999999999999998}
                        {{:a "Dostojevski", :b "komedija", :c "novi vek"}
                         0.26385258936279504}]]
    (is (= expectedresult (recommand-books refBook 2 data)))))
