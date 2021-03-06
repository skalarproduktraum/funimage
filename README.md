funimage
========

[![Join the chat at https://gitter.im/funimage/funimage](https://badges.gitter.im/funimage/funimage.svg)](https://gitter.im/funimage/funimage?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Functional Image Processing with ImageJ/FIJI

[![Build Status](https://travis-ci.org/funimage/funimage.svg?branch=master)](https://travis-ci.org/funimage/funimage)

=========

Note to functional programming folks, funimage is *destructive* by default. Images are large and take up lots of memory, we try not to duplicate data unless necessary. If you want to do something non-destructively, then you need to explicitly use functions like copy-imp.

========

#Usage within ImageJ:

Add an update site like you would usually (see http://fiji.sc/List_of_update_sites)   

where the update site is: http://sites.imagej.net/Funimage/  

=========

#Usage within Clojure projects:

Add the following to your `project.clj`

```
  :repositories [["imagej-releases" "http://maven.imagej.net/content/repositories/releases/"]
                 ["imagej-snapshots" "http://maven.imagej.net/content/repositories/snapshots/"]]
                 
```                 
                 
========                 

Citing:

Functional Image Processing with ImageJ/FIJI
K. Harrington, T. Stiles, L. Venkatraman, C.Prahst, K. Bentley
Proceedings of Bioimage Informatics, 2015, (accepted).

=========

License:

Apache V2.0

Copyright 2014-2015 Kyle Harrington.
