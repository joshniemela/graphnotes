(defproject graphnotes "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url "https://opensource.org/license/mit/"}
  :dependencies [
    [org.clojure/clojure "1.11.1"]
    [org.clojure/tools.cli "1.0.214"]
    [com.github.full-spectrum/neo4clj-core "1.1.0"] ; maybe remove since Asami is used
    [org.clojars.quoll/asami "2.3.3"]
    [buddy/buddy-hashers "1.8.158"]]
  :main ^:skip-aot core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
