# jts-geojson
Simple library for reading and writing GeoJSON files in Java/Kotlin, using JTS Geometry classes.

This library uses a bit of relaxed version of GeoJSON standard. It does not bother with validating some of GeoJSON annoyingly strict
contracts, like requirement of all Features in same file having same set of properties or same type of Geometry. Thus you may 
create files that won't be read properly by some tools that obey standards strictly. But this makes using GeoJSON much easier.

## Usage

Library provides simple classes for `Feature` and `FeatureCollection`, that can directly be read and written using Gson library.

```kotlin
import java.io.OutputStream

val geom = Geometry(...) // JTS Geometry
val feature = Feature(geom, mutableMapOf("prop1" to "val1", "prop2" to "val2")) //
val fc = FeatureCollection(feature)

// Save FC to GeoJSON output stream
GeoJSONImportExport.writeFeatureCollection(OutputStream(...), fc)

// Read GeoJSON file
val fcFromFile = GeoJSONIMportExport.readFeatureCollection(InputStream(...))

// Access feature geometries and properties
fcFromFile.features[0].geometry
fcFromFile.features[1].properties["prop1"]
```
