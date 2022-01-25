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
            (into interceptors)
            (conj {:enter
                   (fn [ctx]
                     (assoc ctx :response (handler ctx)))}))

        request
        {:request-method :get}

        resp
        (ei/execute {:request request} stack)]

    (is (= {:request-method :get}
           (:request @capture)))

    (is (= {:status 200
            :body "{\"foo\":42}"
            :headers {"Content-Type" "application/json; charset=utf-8"}}

           resp))))
