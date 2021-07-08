"use strict";

const compress = new Compress();
const upload = document.getElementById("upload-img");
const imagePreview = document.getElementById('upload-img-preview');
upload.addEventListener("change", uploadImageByCompressing);

function uploadImageByCompressing(evt) {
  const files = [...evt.target.files];
  compress.compress(files, {
    size: 1, // the max size in MB, defaults to 2MB
    quality: 0.75, // the quality of the image, max is 1,
    maxWidth: 800, // the max width of the output image, defaults to 1920px
    maxHeight: 800, // the max height of the output image, defaults to 1920px
    resize: true, // defaults to true, set false if you do not want to resize
    // the image width and height
  })
    .then((images) => {
      console.log(images);
      localStorage.setItem('compressedPostImage', JSON.stringify(images[0]));
      const img = images[0];

      $("#preview-box").css('display', 'block');
      $('#upload-img-preview')
        .attr('src', `${img.prefix}${img.data}`)
        .width('100%');

      console.log(img);
    });
}
