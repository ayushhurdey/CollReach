"use strict";

const compress = new Compress();

const upload = document.getElementById("upload");
const preview = document.getElementById("preview");
const output = document.getElementById("output");
const imgPreview = document.getElementById("imageprev");
const imgdnld = document.getElementById("imgdnld");

upload.addEventListener(
  "change",
  (evt) => {
    const files = [...evt.target.files];
    compress
      .compress(files, {
        size: 4, // the max size in MB, defaults to 2MB
        quality: 0.75, // the quality of the image, max is 1,
        maxWidth: 1920, // the max width of the output image, defaults to 1920px
        maxHeight: 1920, // the max height of the output image, defaults to 1920px
        resize: true, // defaults to true, set false if you do not want to resize
                      // the image width and height
      })
      .then((images) => {
        console.log(images);
        const img = images[0];
        // returns an array of compressed images
        preview.src = `${img.prefix}${img.data}`;
        var image = new Image();
        image.src = `${img.prefix}${img.data}`;
        imgdnld.href = `${img.prefix}${img.data}`;
        imgPreview.src = `${img.prefix}${img.data}`;
        console.log(img);

        const {
          prefix,
          data,
          endSizeInMb,
          initialSizeInMb,
          iterations,
          sizeReducedInPercent,
          elapsedTimeInSeconds,
          alt,
        } = img;

        output.innerHTML = `<b>Start Size:</b> ${initialSizeInMb} MB <br/><b>End Size:</b> ${endSizeInMb} MB <br/><b>Compression Cycles:</b> ${iterations} <br/><b>Size Reduced:</b> ${sizeReducedInPercent} % <br/><b>File Name:</b> ${alt}<br/>`;
      });
  },
  false
);



// ************    Currently this file is not being used anywhere  ***************