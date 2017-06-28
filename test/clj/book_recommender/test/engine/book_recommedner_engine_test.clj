(ns book-recommender.test.engine.book-recommedner-engine-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [book-recommender.engine.book-recommender-engine :refer :all]
            [book-recommender.reader.csv-reader :refer :all]))

(deftest test-recommender
  (testing "generating recommendations for given book")
  (let [data (load-data-from-file "C:\\dev\\projects\\book-recommender\\test\\resource\\testBookDataSet.csv")
        refBook (first data)
        expectedresult [{{:num "one" :author_name "Dostojevski", :author_movement "Drama", :genre "srednji vek"}
                         0.9999999999999998}
                        {{:num "two" :author_name "Dostojevski", :author_movement "komedija", :genre "novi vek"}
                         0.26385258936279504}]]
    (is (= expectedresult (recommand-books refBook 2 data)))))
