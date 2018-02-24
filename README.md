# TensorFlow - Hot or Not?
This is an example of how to use [TensorFlow](https://www.tensorflow.org/) library to classify images.

<div align="center">
<a href="https://imgflip.com/i/257ush"><img width="270" height="480" src="https://i.imgflip.com/257ush.jpg" title="made at imgflip.com"/></a>
<a href="https://imgflip.com/i/257uv9"><img width="270" height="480" src="https://i.imgflip.com/257uv9.jpg" title="made at imgflip.com"/></a>
</div>

<br/>

## Usage

Flow of the app is pretty simple:
1. First of all we're taking a photo.
2. The next step is to classify if it's hot or not.
3. At the end we're showing the results and we can take another photo.

### Code structure

The app consists of two main components:
1. `MainActivity` which is responsible for taking a photo.
2. `ImageClassifier` which classifies the photo.

### CLassifier

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

Before classification the photo needs to be prepared to fit the input of the classifier which is 224x224 pixels. Becouse of that the photo is resized and cropped - this is happening inside the `ImageUtils`.

The prepared photo is passed to the `ImageClassifier`. The class responsibilities are as follows:
1. Nomalizing pixels of the photo - `preprocessImageToNormalizedFloats()` method.
2. Classifying - `classifyImageToOutputs()` method.
3. Getting the results - `getResults()` method.

<br/>

## License
[MIT](https://github.com/pszklarska/LiveDataBinding/blob/master/LICENSE)
