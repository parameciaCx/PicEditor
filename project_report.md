

## PictureEditor Project Report

By: Zizhou Zhang  


Implemented using Java, GUI was implemented for easier navigation and application. The purpose of this report is to show and give insight on the functionalities of the program.

  

## 3x3 Filters:

To apply a 3x3 filter to an image, we first click the browse button to choose the image file we are looking for using the explorer. After selecting you can apply a filter by entering the 3x3 matrix into the 3x3 box and clicking “Apply Filter”.

A simple parser is used to convert the number into a decimal just in case a fraction is inputted.

The filter is applied by identifying the 9 unique positions that a pixel can be in: top left corner, top row, top right corner, first column, last column, bottom left corner, bottom right corner, bottom row, and finally, everything else that are not one of those 8 cases.

After applying the filter, a window will pop up with a tab showing the original image, and another tab showing the image after the given filter has been applied.

  
  
  

## Black and white, Connected regions:

To turn the image into black and white, and analyze the connected regions, click the Analyze button after an image has been selected, a window will pop up with 2 tabs, the B/W tab which shows the image with only black and white pixels, and the Symbol tab which shows the image but the connected regions are colored based on the amount of connected components in the region. The more connected components, the darker the connected region will be. The formula used simply subtracts the region intensity from 255, which is the color white. These connected components are found with a Depth first search. The more connected components, the darker the symbol will be on this new image. The following shows this in action:

![](https://lh5.googleusercontent.com/DPgBt605MDkWJd5X0Lk-g5YjLnMidP9of3TwrlZBwNI8iitT4LQQVegw-j5Owq8odKgx0cou_5rc_bipCfT_U9vkDhuyYwKi2EqDKP_Qd7RVYB5vS1HqpjwnXTvOpGQzfs7G5xHr)

The components and their pixels can easily be printed into the console, the following are the connected components of the above image:

![](https://lh3.googleusercontent.com/r6CwoQeQ-_A-bFeMWqhYp7lc-pZ-4ZvvO79_Px-F7PPSJHg7ME7-UHWGUnnMN3jggBTmMOJBeb_UT_pw5lecQdYKav03kqIK_9KxqXkEnv8TSaULXDAJfl6bnQonr0V3Gw-kJFUf)

But the pictures serves as a better visualization. This analyze function also does other things behind the scenes, like constructing objects that hold the information of the individual connected regions, so we can operate on them later on. Scaling, for example, requires the image to be analyzed before scaling can take place.

  

## Convolution Filter

To apply a 1x3 or 3x1 convolution filter, click the “Convolution” button after an image has been selected, This will give a window with 3 boxes and 2 buttons. The desired convolution matrix is inputted into the 3 boxes, and whether you want to apply a horizontal or vertical convolution matrix, just click the respective button. The image is displayed as these filters are applied in the background of the window. After applying one horizontal ⅓ convolution filter, and one vertical, the image does look the same as a single 3x3 filter of 1/9.

Below, you can see the before and after of the 3x3 1/9 filter applied on a test image.

![](https://lh3.googleusercontent.com/eOUNdh0Gn40Q2mHv1_C8gpPpGbilhHspwQ6jGaBeRdQN5lIg0R97KmpeDeyj1bP5VPfaRIyUgW9E3e0AE12xNxXSDVP7vNLY3uL2KK6tq3gejstFmL9foDzqV82OMhgnj1uA59J2)

And the following is after applying ⅓ convolution filter both vertically and horizontally once.

![](https://lh5.googleusercontent.com/UpZgA8eKtJUQvCTbFHTmQ4Y9fkc4-R1fIUGjc9K7R9dzX-kIU_I0n1kPDWdV14u6HOUIysE4SP3rI6CfGgAOkJznHCKXeCwFXcRYgEIiviZwgHh18BmDdoGI_v8yL-myvwyWnz4j)

As can be observed, with the exception of the black bars, the images look the same after applying these changes in different ways. The second image does look bigger but that is the result of auto scaling from the word processing tool. The color is not maintained from the original image because all images are converted to greyscale before they are operated on.

  

## Scaling, save vectors to file:

Scaling down symbols, getting the feature vectors, outputting them to a file, and testing distance vs the saved file can all be achieved by clicking the “Scale Symbols” button. This button is greyed out in the beginning, because in order to perform this operation, the image needs to be analyzed first.

For scaling, simply enter the desired dimensions each symbol should be scaled to in the X and Y text boxes, and click “Scale Symbols”. To show this, we will use an image with equal sized symbols, as this scales all the symbols on the image to the same size.

Below shows an image with equal sized numbers from 0 to 9 scaled with parameters X as 12 and Y as 31.

![](https://lh6.googleusercontent.com/elUGAj8hp4pFIdaQcwBLHEV3Kk40TaSf43o4DfGKgJCcBjjnoVsMAL-NQqShSfdK2I4dFvbUFfVYSIiAB6c3jmaKl-h04_naAG8-myQsGk9Bk_gUIKvBeuGhFqTB1slslzp_W1iO)

This is the only operation that can be performed unless the user enters 9 for both X and Y. In which case, a few other operations will be available.

When scaling to 9x9, the program will automatically analyze the 9 3x3 regions for each symbol. To calculate these ratios, we use a additive value for each region, starting at a total of 0, each black pixel decreases the total by 1, and each white pixel increases it by 1. For example, -9 means all 9 pixels are black, and 9 means all pixels are white. The purpose of this is to avoid problems like divide by 0 errors that we would encounter using a ratio obtained through division. After this is done in the background, a prompt will come up asking if the user would like to save these vectors to a file named "output.txt", if Yes is clicked, then these vectors will be outputted to that file, in the same directory. Using the same image as the above scaling example, scaling to 9x9 will output a file with the following contents:

![](https://lh4.googleusercontent.com/rpTFVyOR4pcMuOis6EDCsXaJsAtQGcVcxMTZnMv3nLgD0hQGRuGL8_o_xksbNrTXk_4i5EvEVUxabmqAPKB8MfUJt-pj_hl-mD5OwmSCozHZARZLWyi6fso4r7ro_S3qaNX8FawV)

Each line holding the ratios of black to white pixels for that respective symbol, line 1 holds values for number 0, and line 10 holds values for the number 9 symbol. Because of this we need our images to hold the symbols in a way where its ordered from 0 to 9. As can be observed, because of the big block characters in our image, we have many regions with purely black or purely white pixels. After this, another prompt comes up asking if you’d like to compare the current image to the vectors saved in output.txt, if you just clicked yes previously it will be comparing to itself, which will output something like the following:

![](https://lh5.googleusercontent.com/iluI-r36ZxI9RcIk1wb_CzE5xIYCtFyxt4v9DlguxqzYuKgCqbymUlAhf4cHH9AyQ84R0Kgh2Ju3bHIoM0C1YYpdDlM6RpUXX0zikEDIOee3YlVemLmfPuXq7_dE1LnuLFCAPy_r)

The first number of each row signifies the symbol number on the current picture, and the last number represents the symbols in the saved file. The min value being the lowest euclidean distance found after comparing to all symbols, when comparing to itself, of course the euclidean distance would be 0 and it will be identified as the symbol that it is. Identifying images by just using distance vectors is not very effective, as this does not work nearly as well when compared to even an image of a different font. Ideally, we would use a neural network and feed the program hundreds and thousands of training examples so it can make decisions with much higher accuracy. This was the original goal but it proved to be more difficult than expected.

  

## Thinning:

The algorithm used here is a two pass algorithm called the Zhang-Suen algorithm, for this we need to obtain the amount of black neighbours, and the number of times the neighbors transition from white to black, along with other checks across two parts, it will repeat until the image no longer changes from going through the algorithm. The implementation converts the image into a string for easier navigation, and it will convert it back to a buffered image once it has finished thinning. Simply click the Thin button after analyzing the image, when applied on the same picture as above, this is the outcome:

![](https://lh6.googleusercontent.com/flNIkYlODpYjVlikORQNbNmAc3ahaj-x7WMRsKLBy9tyYDF1A3oP6dTxxpjy6bwk08TlVcoyT48IrOxJDMuhK57SCsRvQ5U26s8VPC6nZMekjqzjp7xBxpoeIuIhsVpJQxy32jyj)
