
(apply sorted-map-by

       (fn [key1 key2]
         (let [weights
               {:foo 1
                :bbb 0

                }]
           (compare (get weights key1 999)
                    (get weights key2 999))))

       [:foo :aaa :bbb 42 :ccc "hello"])
