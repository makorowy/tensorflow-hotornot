# TensorFlow - Hot or Not?
This is an example of how to use [TensorFlow](https://www.tensorflow.org/) library to classify images.

<div align="center">
<a href="https://imgflip.com/i/257ush"><img width="270" height="480" src="https://i.imgflip.com/257ush.jpg" title="made at imgflip.com"/></a>
<a href="https://imgflip.com/i/257uv9"><img width="270" height="480" src="https://i.imgflip.com/257uv9.jpg" title="made at imgflip.com"/></a>
</div>

<br/>

## Usage

Flow of the app is pretty simple:
1. Take a photo.
2. Classify if it's hot or not.
3. Show the results.

### Code structure

The app consists of two main components:
1. `MainActivity` which is responsible for taking a photo.
2. `ImageClassifier` which classifies the photo.

### Classifier

`ImageClassifier` properties:
- `inputName` - the name of the classifier's input (the photo pixels goes in there),
- `outputName` - the name of the classifier's output (the results can be found there),
- `imageSize` - the size of the photo,
- `labels` - the list of the labels (in our case "hot" and "not"),
- `imageBitmapPixels` - the array with bitmap pixels (int values before normalization),
- `imageNormalizedPixels` - the array with normalized pixels,
- `results` - the list with the results,
- `tensorFlowInference` - the TensorFlow API object (which is used for inference).

### Classification process

For classifing photos the app is using retrained [MobileNet](https://github.com/tensorflow/models/blob/master/research/slim/nets/mobilenet_v1.md) model. The model can be found inside the `assets` folder together with the labels file.

Before classification the photo needs to be prepared to fit the input of the classifier which is 224x224 pixels. Because of that the photo is resized and cropped which is happening inside the `ImageUtils`.

Prepared photo is passed to the `ImageClassifier`. The class responsibilities are as follows:
1. Nomalizing pixels of the photo - `preprocessImageToNormalizedFloats()` method.
2. Classifying - `classifyImageToOutputs()` method.
3. Getting the results - `getResults()` method.

For the classification process the instance of the `TensorFlowInferenceInterface` is used. The classification looks as follows:
1. Put the data to the classifier:
`tensorFlowInference.feed(inputName, imageNormalizedPixels, 1L, imageSize, imageSize, COLOR_CHANNELS.toLong())`
2. Run the classifier:
`tensorFlowInference.run(arrayOf(outputName), ENABLE_LOG_STATS)`
3. Get the results from the output:
`tensorFlowInference.fetch(outputName, results)`

The results are then passed to the `MainActivity` and shown on the screen.

## License
[MIT](https://github.com/pszklarska/LiveDataBinding/blob/master/LICENSE)
