(ns book-recommender.cosine-similarity.cosine-similarity-calculator)
(defn calculate-scalar-product
      "return double value that represents scalar product of vectors"
      [vectorA vectorB]
      (reduce + (map * vectorA vectorB)))

(defn calculate-intensity-of-vector
      "return double value that represents vector's intensity"
      [v]
      (Math/sqrt (reduce + (map * v v))))

(defn calculate-cosine-similarity
      "return a double value of cosine calculators of two vectors"
      [vectorA vectorB]
      {:pre [(= (count vectorA) (count vectorB))]}
      (let [vectorAIntensity (calculate-intensity-of-vector vectorA)
            vectorBIntensity (calculate-intensity-of-vector vectorB)]
        (if (or (= 0.0 vectorAIntensity) (= 0.0 vectorBIntensity))
          0.0
          (/ (calculate-scalar-product vectorA vectorB)
             (* vectorAIntensity vectorBIntensity)))))
