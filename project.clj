(defproject lineup "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.macro "0.1.1"]
                 [org.clojure/core.logic "0.8-alpha3-SNAPSHOT"]]
  :repositories {"sonatype-snapshots"
                 {:url "http://oss.sonatype.org/content/repositories/snapshots"
                  :snapshots {:checksum :fail :update :always}
                  :releases {:checksum :fail :update :always}
                  :update :always, :checksum :fail}})
