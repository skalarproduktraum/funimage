(ns funimage.test.imagej.ops
  (:use [funimage imp conversion]        
        [clojure.test])
  (:require [funimage.img :as img]
            [funimage.img.cursor :as cursor]
            [funimage.imagej.ops :as ops]))

#_(let [fi (net.imglib2.FinalInterval. (long-array [10 10]))
       tpe (net.imglib2.type.numeric.real.DoubleType.)
       img1 (ops/create-img-CreateImgFromDimsAndType fi tpe)]
   (show-imp (img->imp (ops/math-add-ConstantToArrayImage-AddInt img1 17))))

#_(let [cat (imp->img (open-imp "/Users/kharrington/git/funimage/black_cat.tif"))
       hat (imp->img (open-imp "/Users/kharrington/git/funimage/witch_hat_small.tif"))
       shape (net.imglib2.algorithm.neighborhood.RectangleShape. 3 false)
       adder (fn [img1 img2] (imp-flatten (add-overlay-image (copy-imp (img->imp img1)) (img->imp img2) 110 50 100 true)))]
   (show-imp (tile-imps
               (map (fn [func] (adder cat (func (img/copy hat) shape)))
                    [ops/morphology-dilate-DefaultDilate ops/morphology-erode-DefaultErode]))))

