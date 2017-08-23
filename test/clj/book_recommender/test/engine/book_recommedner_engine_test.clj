(ns book-recommender.test.engine.book-recommedner-engine-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [book-recommender.engine.book-recommender-engine :refer :all]
            [book-recommender.reader.csv-reader :refer :all]))

(deftest test-recommender
  (testing "generating recommendations for given book")
  (let [data (load-data-from-file "C:\\dev\\projects\\book-recommender\\test\\resource\\testBookDataSet.csv")
        refBook (first data)
        expectedresult [{:book
                                {:uri "u2",
                                 :name "second book",
                                 :author_name "Dostojevski",
                                 :author_movement "komedija",
                                 :genre "novi vek"},
                         :value 0.26385258936279504}
                        {:book
                                {:uri "u3",
                                 :name "crime and punishment",
                                 :author_name "Dostojevski",
                                 :author_movement "roman",
                                 :genre "rusija"},
                         :value 0.26385258936279504}]]
    (is (= expectedresult (recommend-books refBook 2 data)))))

(deftest test-recommender-r
  (testing "generating recommendations for given book with implementation of core.reducers")
  (let [data (load-data-from-file "C:\\dev\\projects\\book-recommender\\test\\resource\\testBookDataSet.csv")
        refBook (first data)
        expectedresult [{:book
                                {:uri "u2",
                                 :name "second book",
                                 :author_name "Dostojevski",
                                 :author_movement "komedija",
                                 :genre "novi vek"},
                         :value 0.26385258936279504}
                        {:book
                                {:uri "u3",
                                 :name "crime and punishment",
                                 :author_name "Dostojevski",
                                 :author_movement "roman",
                                 :genre "rusija"},
                         :value 0.26385258936279504}]]
    (is (= expectedresult (recommend-books-r refBook 2 data)))))
