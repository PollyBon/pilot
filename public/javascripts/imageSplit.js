var ImageSplit = (function () {

    const defaultNumberOfThreads = 6;
    const markClassMame = 'image-block-mark';
    const vpHeight = $(window).height();

    let currentLoadings;
    let fragments;

    return {
        init: function (imagePrefix, domEntryPoint, imageHeight, imageWidth) {
            this.imagePrefix = imagePrefix;
            this.imageHeight = imageHeight;
            this.imageWidth = imageWidth;
            this.domEntryPoint = domEntryPoint;
            currentLoadings = 0;

            renderFragments();
            fragments = $("." + markClassMame);
        },
        checkFragments: function () {
            for (let index = 0; index < fragments.length;) {
                if (isVisible(fragments[index])) {
                    setSrc(index);
                } else {
                    index++;
                }
            }
        },
        clear: function () {
            fragments = $("." + markClassMame);
            fragments.children().attr("src", "");
            fragments.remove();
            currentLoadings = 0;
        },
    };

    function renderFragments() {
        let viewPortOccurrences = Math.floor(imageHeight / vpHeight);
        viewPortOccurrences = viewPortOccurrences == 0 ? 1 : viewPortOccurrences;
        let partHeight = Math.floor(imageHeight / viewPortOccurrences / defaultNumberOfThreads);
        let partOccurr = Math.floor(imageHeight / partHeight);
        let lastPartHeight = imageHeight - partHeight * (partOccurr - 1);
        for (let i = 0; i < partOccurr - 1; i++) {
            createDomElement(0, partHeight * i, partHeight, imageWidth).appendTo(domEntryPoint);
        }
        createDomElement(0, partHeight * (partOccurr - 1), lastPartHeight, imageWidth).appendTo(domEntryPoint);
    }

    function createDomElement(x, y, h, w) {
        let imgEl = $("<img />", {
            id: "x=" + x + "&y=" + y + "&h=" + h + "&w=" + w
        });

        return $("<div />", {
            class: markClassMame,
            style: "height:" + h + "px;" + "width:" + w + "px;"
        }).append(imgEl);
    }

    function isVisible(element) {
        let viewPortTop = $(window).scrollTop();
        let viewPortBottom = viewPortTop + $(window).height();
        let elementTop = $(element).offset().top;
        let elementBottom = elementTop + $(element).height();
        return ((elementBottom <= viewPortBottom) && (elementBottom >= viewPortTop))
            || ((elementTop <= viewPortBottom) && (elementTop >= viewPortTop));
    }

    function setSrc(index) {
        let img = fragments[index].childNodes[0];
        img.src = imagePrefix + img.id + '&' + new Date().getTime();
        currentLoadings++;
        fragments.splice(index, 1);
        img.onload = chooseNext.bind(null, img);
    }

    function chooseNext(img) {
        currentLoadings--;
        if (currentLoadings < defaultNumberOfThreads && fragments.length > 0) {
            let closest = getYCoord(fragments[0].childNodes[0]);
            let closestIndex = 0;
            let yCoordImg = getYCoord(img);
            for (index = 1; index < fragments.length; index++) {
                let yCoord = getYCoord(fragments[index].childNodes[0]);
                if (Math.abs(yCoord - yCoordImg) < Math.abs(closest - yCoordImg)) {
                    closest = yCoord;
                    closestIndex = index;
                }
            }
            setSrc(closestIndex);
        }
    }

    function getYCoord(element) {
        return element.id.match(/y=(\d+)/)[1];
    }
}());


