(ns book-recommender.test.finder.book-finder-test
  (:require [clojure.test :refer :all]
            [book-recommender.finder.book-finder :refer :all]
            [book-recommender.reader.csv-reader :refer :all]))

(deftest finder-test
  (testing "finding books that match user's search input")
  (let [data (load-data-from-file "C:\\dev\\projects\\book-recommender\\test\\resource\\testBookDataSet.csv")
        search-input "bOOk"
        expectedresult [{:name "first book",
                         :author_name "Dostojevski",
                         :author_movement "Drama",
                         :genre "srednji vek"},
                        {:name "second book",
                         :author_name "Dostojevski",
                         :author_movement "komedija",
                         :genre "novi vek"},
                        {:name "el book",
                         :author_name "Bajron",
                         :author_movement "drama",
                         :genre "romantizam"}]]
    (is (= expectedresult (find-by-name search-input data)))))