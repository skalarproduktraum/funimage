; @ImagePlus(label="Target image",description="Input image") imp
; @Integer(label="Smoothing steps",description="Number of steps of smoothing",value=0) smoothing-steps
; @OUTPUT ImagePlus imp

(def orig-name (.getTitle imp))
(def directory (ij.IJ/getDirectory "image"))

; Requires the plugin from:
; morphology update site
; and
; http://jvsmicroscope.uta.fi/?q=skeleton_intersections


(if (= (.getType imp) ij.ImagePlus/COLOR_RGB)
  (do (ij.IJ/run imp "8-bit" "")
    (def mask imp))
  (do (def channels (ij.plugin.ChannelSplitter/split imp))

    (def mask (second channels))
    (.setTitle mask (str "C2-" orig-name ))))

(ij.IJ/run mask "Subtract Background..." "rolling=25")

(dotimes [step smoothing-steps]
  (ij.IJ/run mask "Smooth" ""))

(ij.IJ/run mask "Auto Threshold" "method=IsoData white")

(ij.IJ/run mask "Skeletonize (2D/3D)" "")

(ij.IJ/run mask "Analyze Particles..." "size=1000-Infinity pixel show=Masks display clear add")

(def roi-info
  (let [rt (ij.measure.ResultsTable/getResultsTable)]
    {:num-regions (.getCounter rt)
     :area (apply + (map #(.getValue rt "Area" %) (range (.getCounter rt))))}))

;(let [rm (ij.plugin.frame.RoiManager.)
;      num-rois (.getCount rm)]


(def new-mask (ij.WindowManager/getImage (str "Mask of " (str "C2-" orig-name ))))

(ij.IJ/run new-mask "Invert LUT" "")

;(.setTitle new-mask orig-name)

(ij.IJ/run new-mask "Skeleton Intersections" "pseudo results")

(let [ic (ij.plugin.ImageCalculator.)
	  intersections (ij.WindowManager/getImage "Skeleton intersections")]
	(.run ic "add" imp intersections))

(set! (.changes new-mask) false)
(.close new-mask)

#_(let [rt (ij.measure.ResultsTable/getResultsTable)]
   (.setValue rt "Area" 0 (double (:area roi-info)))
   (.setValue rt "NumRegions" 0 (double (:num-regions roi-info)))
   (.updateResults rt))

(let [tp (ij.IJ/getTextPanel)
      rt (.getResultsTable tp)
      ;tmp (println (clojure.string/join "\t" (.getHeadings rt)))
      line (.getLine tp 0)
      [image-name num-intersections] (clojure.string/split line #"\t")
      ;image-name (.getValue rt "Image name" 0)
      ;num-intersections (.getValue rt "Num of intersections" 0)
      ]
  (ij.IJ/setColumnHeadings "Image name\tNum of intersections\tArea\tNum regions")
  (.append (ij.IJ/getTextPanel)
    (str image-name "\t" num-intersections "\t" (:area roi-info) "\t" (:num-regions roi-info))))

;(println "saving as" (str (string/replace (string/replace orig-name ".tif" "") " " "_") "_labeled_branches.tif"))
;(ij.IJ/saveAsTiff imp (str directory "/" (string/replace orig-name ".tif" "") "_labeled_branches.tif"))

;(ij.IJ/run "Close All")
