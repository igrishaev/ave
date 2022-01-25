
(apply sorted-map-by

       (fn [key1 key2]
         (let [weights
               {:foo 1
                :bbb 0

                }]
           (compare (get weights key1 999)
                    (get weights key2 999))))

       [:foo :aaa :bbb 42 :ccc "hello"])


{:interceptors
 :router
 [["/api*"] {:interceptors [... ... ...]
             :router [["/user"] {:get :handler.api/list-users
                                 :post :handler.api/create-user}

                      ["/order/:id"] {:get :handler.api/list-users
                                      :post :handler.api/create-user}

                      ]}]}

(ns ave.interceptor.util)


(defn handler->interceptor
  [handler]
  {:enter
   (fn [ctx]
     (assoc ctx :response (handler ctx)))})


(def interceptor-leave-response
  {:leave :response})


(defn wrap-stack
  [interceptors handler]
  (-> [interceptor-leave-response]
      (into interceptors)
      (conj (handler->interceptor handler))))
