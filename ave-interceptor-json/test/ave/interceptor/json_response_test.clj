(ns ave.interceptor.json-response-test
  (:require
   [ave.interceptor.json-response :as json-response]

   [exoscale.interceptor :as interceptor]
   [integrant.core :as ig]

   [clojure.test :refer [is deftest]]))


(deftest test-ok

  (let [interceptor
        (ig/init-key ::json-response/* nil)

        capture
        (atom nil)

        handler
        (fn [ctx]
          (reset! capture ctx)
          {:status 200
           :body {:foo 42}})

        stack
        (-> []
            (conj {:leave :response})
            (conj interceptor)
            (conj {:enter
                   (fn [ctx]
                     (assoc ctx :response (handler ctx)))}))

        request
        {:request-method :get}

        resp
        (interceptor/execute {:request request} stack)]

    (is (= {:request-method :get}
           (:request @capture)))

    (is (= {:status 200
            :body "{\"foo\":42}"
            :headers {"Content-Type" "application/json; charset=utf-8"}}

           resp))))
