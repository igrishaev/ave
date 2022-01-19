(defproject ave "0.1.0-SNAPSHOT"

  :description
  "A battery-included framework for Clojure"

  :url
  "https://github.com/igrishaev/ave"

  :license
  {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
   :url "https://www.eclipse.org/legal/epl-2.0/"}

  :plugins
  [[lein-sub "0.3.0"]
   [exoscale/lein-replace "0.1.1"]]

  :sub ["ave-core"
        "ave-server-jetty"
        "ave-interceptor-json"
        "ave-router-reitit"
        ]

  :dependencies
  []

  :managed-dependencies
  [[ave/core :version]
   [ave/server-jetty :version]
   [ave/interceptor-json :version]
   [ave/router-reitit :version]

   [org.clojure/clojure "1.10.3"]
   [ch.qos.logback/logback-classic "1.2.3"]
   [cheshire "5.10.0"]
   [clj-http "3.12.0"]
   [org.clojure/tools.logging "1.1.0"]
   [ring-basic-authentication "1.1.0"]
   [metosin/reitit-core "0.5.15"]
   [ring/ring-jetty-adapter "1.9.4"]
   [ring/ring-json "0.5.1"]
   [selmer "1.12.34"]
   [integrant "0.8.0"]
   [metosin/sieppari "0.0.0-alpha13"]
   [exoscale/interceptor "0.1.9"]
   [manifold "0.2.3"]]

  :test-selectors
  {:all         (constantly true)
   :default     (complement :integration)
   :integration :integration}

  :profiles
  {:dev
   {:source-paths ["dev"]

    :dependencies
    [[org.clojure/clojure]
     [ch.qos.logback/logback-classic]]

    :global-vars
    {*assert* true
     *warn-on-reflection* true}

    :jvm-opts ["-Dclojure.spec.compile-asserts=true"
               "-Dclojure.spec.check-asserts=true"]}

   :uberjar
   {:aot :all
    :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
