(ns ave.interceptor.json-response-test
  (:require

   [ave.interceptor.json-response :as json-resp]

   [exoscale.interceptor :as ei]

   ;; [integrant.core :as ig]
   ;; [ring.middleware.json :as json]

   ;; [clojure.spec.alpha :as s]

   [ave.interceptor.util :as util]

   [clojure.test :refer [is deftest]]

   ))


(deftest test-ok

  (let [interceptors
        [json-resp/default]

        handler
        (fn [ctx]
          {:status 200
           :body {:foo 42}})

        stack
        (util/wrap-stack interceptors handler)

        request
        {}

        resp
        (ei/execute {:request request} stack)]

    (is (= {:status 200
            :body "{\"foo\":42}"
            :headers {"Content-Type" "application/json; charset=utf-8"}}

           resp)))

  )
