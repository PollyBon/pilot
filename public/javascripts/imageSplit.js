var ImageSplit = (function () {
        let currentLoadings;
        let fragments;

        return {
            init: function (imagePrefix, numberOfThreads, markClassMame) {
                this.imagePrefix = imagePrefix;
                this.numberOfThreads = numberOfThreads;
                this.markClassMame = markClassMame;
                currentLoadings = 0;
                fragments = $(markClassMame);
            },
            checkFragments: function () {
                for (index = 0; index < fragments.length;) {
                    if (isVisible(fragments[index])) {
                        setSrc(index);
                    } else {
                        index++;
                    }
                }
            },
            clear: function () {
                $(markClassMame).children().attr("src", "");
                fragments = $(markClassMame);
                currentLoadings = 0;
            }
        };

        function isVisible(element) {
            let viewPortTop = $(window).scrollTop();
            let viewPortBottom = viewPortTop + $(window).height();
            let elementTop = $(element).offset().top;
            let elementBottom = elementTop + $(element).height();
            return ((elementBottom <= viewPortBottom) && (elementBottom >= viewPortTop))
                || ((elementTop <= viewPortBottom) && (elementTop >= viewPortTop));
        }

        function setSrc(index) {
            let img = fragments[index].childNodes[1];
            img.src = imagePrefix + img.id + '&' + new Date().getTime();
            currentLoadings++;
            fragments.splice(index, 1);
            img.onload = chooseNext.bind(null, img);
        }

        function chooseNext(img) {
            currentLoadings--;
            if (currentLoadings < numberOfThreads && fragments.length > 0) {
                let closest = getYCoord(fragments[0].childNodes[1]);
                let closestIndex = 0;
                let yCoordImg = getYCoord(img);
                for (index = 1; index < fragments.length; index++) {
                    let yCoord = getYCoord(fragments[index].childNodes[1]);
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


