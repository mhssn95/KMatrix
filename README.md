# KMatrix
A simple matrix implementation for kotlin using Kotlin DSL
# Usage
##### Creating a matrix
```kotlin
val matrix = Matrix {
    row(1, 2, 3)
    row(4, 5, 6)
    row(7, 8, 9)
}
```
##### Get matrix dimensions size
```kotlin
val matrix = Matrix {
    row(2, 4)
    row(8, 10)
    row(9, 22)
}
val rowSize = matrix.rowSize
val columnSize = matrix.columnSize
println("$rowSize * $columnSize matrix")
```
```
3 * 2 matrix
```
##### Get a matrix element
```kotlin
val element = matrix[1, 2]
```
##### Get a matrix row elements
```kotlin
val row = matrix.getRow(0)
```
##### Get a matrix column elements
```kotlin
val column = matrix.getColumn(0)
```
##### Multiplay two matrices
```kotlin
val scale = Matrix {
    row(5, 0, 0)
    row(0, 5, 0)
    row(0, 0, 5)
}
val point = Matrix {
    row(10)
    row(7)
    row(2)
}
val result = scale * point
println(result)
```
```
┌──────┐
│ 50.0 │
├──────┤
│ 35.0 │
├──────┤
│ 10.0 │
└──────┘
```
##### Adding two matrices
```kotlin
val matrix1 = Matrix {
    row(1, 2, 3)
    row(4, 5, 6)
    row(7, 8, 9)
}
val matrix2 = Matrix {
    row(1, 2, 3)
    row(4, 5, 6)
    row(7, 8, 9)
}
val result = matrix1 + matrix2
println(result)
```
```
┌──────┬──────┬──────┐
│ 2.0  │ 4.0  │ 6.0  │
├──────┼──────┼──────┤
│ 8.0  │ 10.0 │ 12.0 │
├──────┼──────┼──────┤
│ 14.0 │ 16.0 │ 18.0 │
└──────┴──────┴──────┘
```
##### Subtract two matrices
```kotlin
val matrix1 = Matrix {
    row(2, 4, 6)
    row(8, 10, 12)
    row(14, 16, 18)
}
val matrix2 = Matrix {
    row(1, 2, 3)
    row(4, 5, 6)
    row(7, 8, 9)
}
val result = matrix1 - matrix2
println(result)
```
```
┌─────┬─────┬─────┐
│ 1.0 │ 2.0 │ 3.0 │
├─────┼─────┼─────┤
│ 4.0 │ 5.0 │ 6.0 │
├─────┼─────┼─────┤
│ 7.0 │ 8.0 │ 9.0 │
└─────┴─────┴─────┘
```
##### Get the transpose of a matrix
```kotlin
val matrix = Matrix {
    row(2, 4)
    row(8, 10)
}
val transposed = matrix.transpose()
println(transposed)
```
```
┌─────┬──────┐
│ 2.0 │ 8.0  │
├─────┼──────┤
│ 4.0 │ 10.0 │
└─────┴──────┘
```
##### Mapping a matrix
```kotlin
val matrix = Matrix {
    row(2, 4, 6)
    row(8, 10, 12)
    row(14, 16, 18)
}
val newMatrix = matrix.map {
    it/2
}
println(newMatrix)
```
```
┌─────┬─────┬─────┐
│ 1.0 │ 2.0 │ 3.0 │
├─────┼─────┼─────┤
│ 4.0 │ 5.0 │ 6.0 │
├─────┼─────┼─────┤
│ 7.0 │ 8.0 │ 9.0 │
└─────┴─────┴─────┘
```
##### Get the element's first indexes
```kotlin
val matrix = Matrix {
    row(2, 4, 6)
    row(8, 10, 12)
    row(14, 16, 18)
}
val index = matrix.indexOf(2.0)
println(index)
```
```
(0, 0)
```
##### Get the element's last indexes

```kotlin
val matrix = Matrix {
    row(2, 4, 6)
    row(8, 10, 12)
    row(14, 16, 2)
}
val index = matrix.lastIndexOf(2.0)
println(index)
```
```
(2, 2)
```
##### Iterate on a matrix
Using forEach
```kotlin
val matrix = Matrix {
    row(2, 4)
    row(8, 10)
}
matrix.forEach {
    println(it)
}
```
```
2.0
4.0
8.0
10.0
```
Using forEachIndexed
```kotlin
val matrix = Matrix {
    row(2, 4)
    row(8, 10)
}
matrix.forEachIndexed { x, y, d ->
    println("($x, $y) = $d")
}
```
```
(0, 0) = 2.0
(1, 0) = 4.0
(0, 1) = 8.0
(1, 1) = 10.0
```
##### Convert matrix to list
```kotlin
val matrix = Matrix {
    row(2, 4)
    row(8, 10)
}
val list = matrix.toList()
println(list)
```
```
[2.0, 4.0, 8.0, 10.0]
```
##### Get the matrix elements sum

```kotlin
val matrix = Matrix {
    row(2, 4, 6)
    row(8, 10, 12)
    row(14, 16, 2)
}
val sum = matrix.sum()
```


##### Check if the matrix contains a value

```kotlin
val matrix = Matrix {
    row(2, 4, 6)
    row(8, 10, 12)
    row(14, 16, 2)
}
val flag = matrix.contains(4)
```
##### Check if the matrix contains a list of values
```kotlin
val matrix = Matrix {
    row(2, 4, 6)
    row(8, 10, 12)
    row(14, 16, 2)
}
val flag = matrix.containsAll(listOf(2, 4, 10))
```
# Download
🚧 Working on it 🚧

# License

```
   Copyright 2022 mhssn

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

