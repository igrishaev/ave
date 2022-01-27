(defproject com.github.igrishaev/ave-interceptor-json "0.1.0-SNAPSHOT"

  :description
  "JSON interceptor for Ave"

  :plugins
  [[lein-parent "0.3.8"]]

  :parent-project
  {:path "../project.clj"
   :inherit [:deploy-repositories
             :license
             :managed-dependencies
             :plugins
             :repositories
             :scm
             :test-selectors
             :url
             [:profiles :dev]]}

  :profiles
  {:test
   {:dependencies
    [[ring/ring-mock "0.4.0"]]}

   :dev
   {:resource-paths ["../dev-resources"]
    :dependencies
    []}}

  :dependencies
  [[integrant]
   [cheshire]
   [exoscale/interceptor]
   [ring/ring-json]])
