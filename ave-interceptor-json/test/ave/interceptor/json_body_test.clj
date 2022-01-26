(ns ave.interceptor.json-body-test
  (:require
   [ave.interceptor.json-body :as json-body]

   [ring.mock.request :as mock]
   [exoscale.interceptor :as interceptor]
   [integrant.core :as ig]

   [clojure.test :refer [is deftest]]))


(deftest test-ok

  (let [interceptor
        (ig/init-key ::json-body/* nil)

        request
        (-> (mock/request :post "/api")
            (mock/json-body {:foo {:bar 42}}))

        capture
        (atom nil)

        handler
        (fn [{:keys [request]}]
          (reset! capture request)
          {:status 200
           :body "ok"})

        stack
        (-> []
            (conj {:leave :response})
            (conj interceptor)
            (conj {:enter
                   (fn [ctx]
                     (assoc ctx :response (handler ctx)))}))

        resp
        (interceptor/execute {:request request} stack)]

    (is (= {:request-method :post
            :body {"foo" {"bar" 42}}}
           (select-keys @capture [:request-method
                                  :body])))

    (is (= {:status 200 :body "ok"}
           resp))))


(deftest test-malformed-default

  (let [interceptor
        (ig/init-key ::json-body/* nil)

        request
        (-> (mock/request :post "/api")
            (mock/body "asdfsd{[222---")
            (mock/header "content-type" "application/json"))

        handler
        (fn [{:keys [request]}]
          {:status 200
           :body "ok"})

        stack
        (-> []
            (conj {:leave :response})
            (conj interceptor)
            (conj {:enter
                   (fn [ctx]
                     (assoc ctx :response (handler ctx)))}))

        resp
        (interceptor/execute {:request request} stack)]

    (is (= {:status 400
            :body "Malformed JSON in request body."
            :headers {"Content-Type" "text/plain"}}

           resp))))


(deftest test-malformed-custom

  (let [interceptor
        (ig/init-key ::json-body/*
                     {:malformed-response
                      {:status 499
                       :body "JSON is wrong"}})

        request
        (-> (mock/request :post "/api")
            (mock/body "asdfsd{[222---")
            (mock/header "content-type" "application/json"))

        stack
        (-> []
            (conj {:leave :response})
            (conj interceptor))

        resp
        (interceptor/execute {:request request} stack)]

    (is (= {:status 499
            :body "JSON is wrong"}

           resp))))
