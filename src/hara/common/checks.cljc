(ns hara.common.checks
  #?(:cljs (:require [goog])))

;; ## Type Predicates
;;
;; Adds additional type predicates that are not in clojure.core

(defn boolean?
  "Returns `true` if `x` is of type `java.lang.Boolean`.

   (boolean? true)   => true
   (boolean? false)  => true"
  {:added "2.0"}
  [x]
  #?(:clj  (instance? java.lang.Boolean x)
     :cljs (goog/isBoolean x)))

(defn hash-map?
  "Returns `true` if `x` implements `clojure.lang.APersistentMap`.

   (hash-map? {})    => true
   (hash-map? [])    => false"
  {:added "2.0"}
  [x]
  #?(:clj  (instance? clojure.lang.APersistentMap x)
     :cljs (satisfies? IMap x)))

#?(:clj
   (defn lazy-seq?
     "Returns `true` if `x` implements `clojure.lang.LazySeq`.

      (lazy-seq? (map inc [1 2 3]))  => true
      (lazy-seq? ())    => false"
     {:added "2.1"}
     [x] (instance? clojure.lang.LazySeq x)))

#?(:clj
   (defn long?
     "Returns `true` if `x` is of type `java.lang.Long`.

      (long? 1)          => true
      (long? 1N)         => false"
     {:added "2.0"}
     [x] (instance? java.lang.Long x)))

#?(:clj
   (defn bigint?
     "Returns `true` if `x` is of type `clojure.lang.BigInt`.

      (bigint? 1N)       => true
      (bigint? 1)        =>  false"
     {:added "2.0"}
     [x] (instance? clojure.lang.BigInt x)))

#?(:clj
   (defn double?
     "Returns `true` if `x` is of type `java.lang.Double`.

      (double? 1)            => false
      (double? (double 1))   => true"
     {:added "2.1"}
     [x] (instance? java.lang.Double x)))

#?(:clj
   (defn bigdec?
     "Returns `true` if `x` is of type `java.math.BigDecimal`.

      (bigdec? 1M)       => true
      (bigdec? 1.0)      => false"
     {:added "2.0"}
     [x] (instance? java.math.BigDecimal x)))

#?(:clj
   (defn instant?
     "Returns `true` if `x` is of type `java.util.Date`.

      (instant? (java.util.Date.)) => true"
     {:added "2.0"}
     [x] (instance? java.util.Date x)))

(defn uuid?
  "Returns `true` if `x` is of type `java.util.UUID`.

   (uuid? (java.util.UUID/randomUUID)) => true"
  {:added "2.0"}
  [x]
  #?(:clj  (instance? java.util.UUID x)
     :cljs (instance? UUID x)))

#?(:clj
   (defn uri?
     "Returns `true` if `x` is of type `java.net.URI`.

      (uri? (java.net.URI. \"http://www.google.com\")) => true"
     {:added "2.0"}
     [x] (instance? java.net.URI x)))

#?(:clj
   (defn url?
     "Returns `true` if `x` is of type `java.net.URL`.

      (url? (java.net.URL. \"file:/Users/chris/Development\")) => true"
     {:added "2.2"}
     [x] (instance? java.net.URL x)))

#?(:clj
   (defn regex?
     "Returns `true` if `x` implements `clojure.lang.IPersistentMap`.

      (regex? #\"\\d+\") => true
     "
     {:added "2.0"}
     [x] (instance? java.util.regex.Pattern x)))

#?(:clj
   (defn bytes?
     "Returns `true` if `x` is a primitive `byte` array.

      (bytes? (byte-array 8)) => true"
     {:added "2.0"}
     [^Object x]
     (= (Class/forName "[B")
        (.getClass x))))

(defn atom?
  "Returns `true` if `x` is of type `clojure.lang.Atom`.

   (atom? (atom nil)) => true"
  {:added "2.0"}
  [obj]
  #?(:clj  (instance? clojure.lang.Atom obj)
     :cljs (satisfies? IAtom obj)))

#?(:clj
   (defn ref?
     "Returns `true` if `x` is of type `clojure.lang.Ref`.

      (ref? (ref nil)) => true"
     {:added "2.0"}
     [obj]
     (instance? clojure.lang.Ref obj)))

#?(:clj
   (defn agent?
     "Returns `true` if `x` is of type `clojure.lang.Agent`.

      (agent? (agent nil)) => true"
     {:added "2.0"}
     [obj]
     (instance? clojure.lang.Agent obj)))

#?(:clj
   (defn iref?
     "Returns `true` if `x` is of type `clojure.lang.IRef`.

      (iref? (atom 0))  => true
      (iref? (ref 0))   => true
      (iref? (agent 0)) => true
      (iref? (promise)) => false
      (iref? (future))  => false"
     {:added "2.0"}
     [obj]
     (instance? clojure.lang.IRef obj)))

(defn ideref?
  "Returns `true` if `x` is of type `java.lang.IDeref`.

   (ideref? (atom 0))  => true
   (ideref? (promise)) => true
   (ideref? (future))  => true"
  {:added "2.0"}
  [obj]
  #?(:clj  (instance? clojure.lang.IDeref obj)
     :cljs (satisfies? IDeref obj)))

#?(:clj
   (defn promise?
     "Returns `true` is `x` is a promise

      (promise? (promise)) => true
      (promise? (future))  => false"
     {:added "2.0"}
     [^Object obj]
     (let [^String s (.getName ^Class (type obj))]
       (.startsWith s "clojure.core$promise$"))))

#?(:clj
   (defn thread?
     "Returns `true` is `x` is a thread

      (thread? (Thread/currentThread)) => true"
     {:added "2.2"}
     [obj]
     (instance? java.lang.Thread obj)))

#?(:clj
   (defn type-checker
     "Returns the checking function associated with `k`

      (type-checker :string) => #'clojure.core/string?

      (require '[hara.common.checks :refer [bytes?]])
      (type-checker :bytes)  => #'hara.common.checks/bytes?"
     {:added "2.0"}
     [k]
     (resolve (symbol (str (name k) "?")))))
