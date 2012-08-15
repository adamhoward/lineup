(ns lineup.core
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic]
        [clojure.tools.macro :as macro]))

(comment
  (run* [q]
        (== q true)))

(comment
  (run* [q]
        (appendo [] q [1 2 3 4 5 6 7 8 9 10])))

(comment (run 1 [q]
              (fresh [vars]
                     (== q vars)
                     (everyg #(infd % (domain 1 2 3 4 5 6 7 8 9)) (repeatedly 81 lvar)))))

(defn lineupo [roster ps]
  (macro/symbol-macrolet [_ (lvar)]
                         (all
                          (== [_ _ _ _ _] ps))))