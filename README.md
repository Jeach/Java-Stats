Java-Stats
==========

Java Stats is a framework providing statistical computation on data-sets.  

Currently supported statistics are mean, median, mode, mid range, minimum, maximum, summation, subtraction, division, multiplication, range, largest(Nth), smallest(Nth), rank(k), frequency(k), distance, squared distance, variance, covariance, standard deviation, weighted averages and a few others.  

With each subsequent releases additional statistical formulas will be implemented (hopefully with some knowledgeable help).  

Dataset support is quite extensive, allowing for easy manipulation and full flexibility. You can populate datasets with Java's native data-types or any objects which extend the Number interface. Each dataset can generate sub-sets by invoking methods such as greater than, less than, equal to, first(Nth), last(Nth), positives, negatives, largest(Nth), smallest(Nth), unique, duplicates and many others. Datasets may be easily ordered and/or reversed as required and shallow copies can easily be produced.  

There is also a wide range of support for correlating data to dataset values and for later manipulating such correlated data.  

Input/Output features are currently being implemented in order to read from or write to a wide variety of files formats such as XML, CSV, tab-delimited, space-delimited, row-based, column-based, etc.

There is an intention to support n-bit integer and n-bit floating point sizes for custom precision in calculations.  Currently, statistical computations may differ drastically in precision when comparing the results computed by this Java library and from those computed with an IEEE 754 quadruple precision floating-point format in a C program.  Providing for any n-precision would be considerably slower, but provide for unlimited precision.
