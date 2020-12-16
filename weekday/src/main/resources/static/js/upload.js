import { updateUserAvatar } from "./action/userInfoAction.js";


function webUploader() {
    let $ = jQuery,
        $wrap = $('#uploader'),
        $queue = $('<ul class="filelist"></ul>').appendTo($wrap.find('.queueList')),
        $statusBar = $wrap.find('.statusBar'),
        $info = $statusBar.find('.info'),
        $upload = $wrap.find('.uploadBtn'),
        $placeHolder = $wrap.find('.placeholder'),
        $progress = $statusBar.find('.progress').hide(),
        fileCount = 0,
        fileSize = 0,
        ratio = window.devicePixelRatio || 1,
        thumbnailWidth = 110 * ratio,
        thumbnailHeight = 110 * ratio,
        state = 'pedding',
        percentages = {},
        supportTransition = (function () {
            let s = document.createElement('p').style,
                r = 'transition' in s || 'WebkitTransition' in s || 'MozTransition' in s ||
                    'msTransition' in s || 'OTransition' in s;
            s = null;
            return r;
        })(),
        uploader;

    if (!WebUploader.Uploader.support()) {
        alert('Web Uploader is not support your browser!');
        throw new Error('WebUploader does not support the browsers you are using.');
    }

    uploader = WebUploader.create({
        auto: false,
        swf: '/static/js/Uploader.swf',
        server: 'testUpload.do',
        pick: {
            id: '#filePicker',
            innerHTML: 'select photo',
            multiple: true
        },
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png,svg',
            mimeType: 'image/*'
        },
        method: 'POST',
    });

    // uploader.addButton({
    //     id: '#filePicker3',
    //     label: 'continue add'
    // });

    function addFile(file) {
        let $li = $('<li id="' + file.id + '">' + '<p class="title">' + file.name + '</p>' + '<p class="imgWrap"></p>' +
            '<p class="progress"><span></span></p></li>'),
            $btns = $('<div class="file-panel">' +
                '<span class="cancel">delete</span>' +
                '<span class="rotateRight">turn right</span>' +
                '<span class="rotateLeft">turn left</span></div>').appendTo($li),
            $prgress = $li.find('p.progress span'),
            $wrap = $li.find('p.imgWrap'),
            $info = $('<p class="error"></p>'),
            text = null,
            showError = function (code) {
                switch (code) {
                    case 'exceed_size':
                        text = 'exceed limit';
                        break;
                    case 'interrupt':
                        text = 'upload pause';
                        break;
                    default:
                        text = 'upload fail please try again';
                        break;
                }
                $info.text(text).appendTo($li);
            };
        if (file.getStatus() === 'invalid') {
            showError(file.statusText);
        } else {
            $wrap.text('Previewing');
            // 生成缩略图
            uploader.makeThumb(file, function (error, src) {
                if (error) {
                    $wrap.text('Can\'t preview');
                    return;
                }
                let img = $('<img src="' + src + '">');
                $wrap.empty().append(img);
            }, thumbnailWidth, thumbnailHeight);
            percentages[file.id] = [file.size, 0];
            file.rotation = 0;
        }
        file.on('statuschange', function (cur, prev) {
            if (prev === 'progress') {
                $progress.hide().width(0);
            } else if (prev === 'queued') {
                $li.off('mouseenter mouseleave');
                $btns.remove();
            }
            if (cur === 'error' || cur === 'invalid') {
                console.log(file.statusText);
                showError(file.statusText);
                percentages[file.id][1] = 1;
            } else if (cur === 'interrupt') {
                showError('interrupt');
            } else if (cur === 'queued') {
                percentages[file.id][1] = 0;
            } else if (cur === 'progress') {
                $info.remove();
                $prgress.css('display', 'block');
            } else if (cur === 'complete') {
                $li.append('<span class="success"></span>');
            }
            $li.removeClass('state-' + prev).addClass('state-' + cur);
        });
        // 鼠标进入，离开
        $li.on('mouseenter', function () {
            $btns.stop().animate({height: 30});
        });
        $li.on('mouseleave', function () {
            $btns.stop().animate({height: 0});
        });
        $btns.on('click', 'span', function () {
            let index = $(this).index(), deg;
            // 图片旋转，删除
            switch (index) {
                case 0:
                    uploader.removeFile(file);
                    return;
                case 1:
                    file.rotation += 90;
                    break;
                case 2:
                    file.rotation -= 90;
                    break;
            }
            if (supportTransition) {
                deg = 'rotate(' + file.rotation + 'deg)';
                $wrap.css({
                    '-webkit-transform': deg,
                    '-mos-transform': deg,
                    '-o-transform': deg,
                    'transform': deg
                });
            } else {
                $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='
                    + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
            }
        });
        $li.appendTo($queue);
    }

    function removeFile(file) {
        let $li = $('#' + file.id);
        delete percentages[file.id];
        updateTotalProgress();
        $li.off().find('.file-panel').off().end().remove();
    }

    // 文件上传进度
    function updateTotalProgress() {
        let loaded = 0,
            total = 0,
            spans = $progress.children(),
            percent;
        $.each(percentages, function (k, v) {
            total += v[0];
            loaded += v[0] * v[1];
        });
        percent = total ? loaded / total : 0;
        spans.eq(0).text(Math.round(percent * 100) + '%');
        spans.eq(1).css('width', Math.round(percent * 100) + '%');
        updateStatus();
    }

    function updateStatus() {
        let text = '', stats;
        if (state === 'ready') {
            text = 'select ' + fileCount + ' pictures，total size' + WebUploader.formatSize(fileSize) + '.';
        } else if (state === 'confirm') {
            stats = uploader.getStats();
            if (stats.uploadFailNum) {
                text = 'Successfully uploaded ' + stats.successNum + ' photo, ' + stats.uploadFailNum +
                    'photos failed to upload, ' +
                    '<a class="retry" href="#">retry</a> fail photo or <a class="ignore" href="#">ignore.</a>'
            }
        } else {
            // check uploader stats
            stats = uploader.getStats();
            // info file size
            // 只能传一张图片，去掉图片张数只显示大小
            text = 'total ' + fileCount + ' pictures (' + WebUploader.formatSize(fileSize) + '), has upload '
                + stats.successNum + ' pictures';
            // file upload fail
            if (stats.uploadFailNum) {
                text += ', fail ' + stats.uploadFailNum + ' pictures';
            }
        }
        // show info
        $info.html(text);
    }

    function setState(val) {
        let file, stats;
        if (val === state) {
            return;
        }
        $upload.removeClass('state-' + state);
        $upload.addClass('state-' + val);
        state = val;
        switch (state) {
            case 'pedding':
                $placeHolder.removeClass('element-invisible');
                $queue.parent().removeClass('filled');
                $queue.hide();
                $statusBar.addClass('element-invisible');
                uploader.refresh();
                break;
            case 'ready':
                $placeHolder.addClass('element-invisible');
                $('#filePicker2').removeClass('element-invisible');
                $queue.parent().addClass('filled');
                $queue.show();
                $statusBar.removeClass('element-invisible');
                uploader.refresh();
                break;
            case 'uploading':
                $('#filePicker2').addClass('element-invisible');
                $progress.show();
                $upload.text('pause upload');
                break;
            case 'paused':
                $progress.show();
                $upload.text('continue upload');
                break;
            case 'confirm':
                $progress.hide();
                $upload.text('start upload').addClass('disabled');
                stats = uploader.getStats();
                if (stats.successNum && !stats.uploadFailNum) {
                    setState('finish');
                    return;
                }
                break;
            case 'finish':
                stats = uploader.getStats();
                if (stats.successNum) {
                    file = uploader.getFiles();
                    uploader.removeFile(file, true);
                    $("#user-info").fadeIn();
                    $("#upload-avatar").hide();
                } else {
                    // 没有成功的图片，重设
                    state = 'done';
                    location.reload();
                }
                break;
        }
        updateStatus();
    }

    uploader.onUploadProgress = function (file, percentage) {
        let $li = $('#' + file.id),
            $percent = $li.find('.progress span');
        $percent.css('width', percentage * 100 + '%');
        percentages[file.id][1] = percentage;
        updateTotalProgress();
    };
    uploader.onFileQueued = function (file) {
        fileCount++;
        fileSize += file.size;
        if (fileCount === 1) {
            $placeHolder.addClass('element-invisible');
            $statusBar.show();
        }
        addFile(file);
        setState('ready');
        updateTotalProgress();
    };
    uploader.onFileDequeued = function (file) {
        fileCount--;
        fileSize -= file.size;
        if (!fileCount) {
            setState('pedding');
        }
        removeFile(file);
        updateTotalProgress();
    };
    uploader.on('all', function (type) {
        let stats;
        switch (type) {
            case 'uploadFinished':
                setState('confirm');
                break;
            case 'startUpload':
                setState('uploading');
                break;
            case 'stopUpload':
                setState('paused');
                break;
        }
    });
    uploader.onError = function (code) {
        alert('Error: ' + code);
    };
    // after click upload
    $upload.on('click', function () {
        if ($(this).hasClass('disabled')) {
            return false;
        }
        if (state === 'ready') {
            uploader.upload();
        } else if (state === 'paused') {
            uploader.upload();
        } else if (state === 'uploading') {
            uploader.stop();
        }
    });
    uploader.on('uploadSuccess', function (file, response) {
        $("#user-avatar").attr("src", response._raw);
        updateUserAvatar(response._raw);
    });
    $info.on('click', '.retry', function () {
        uploader.retry();
    });
    $info.on('click', '.ignore', function () {
        alert('todo');
    });
    $upload.addClass('state-' + state);
    updateTotalProgress();
}

export { webUploader }