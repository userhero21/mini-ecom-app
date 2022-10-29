$.fn.colorSelect = function() {
    function build($select) {
        var html = '';
        var listItems = '';

        $select.find('option').each(function() {
            listItems += ''+
                '<li style="background:'+this.value+'" data-colorVal="'+this.value+'">'+
                '<span>'+this.text+'</span>'+
                '</li>'
            ;
        });

        html = ''+
            '<div class="color-select">'+
            '<span>select one</span>'+
            '<ul>'+listItems+'</ul>'+
            '</div>'
        ;

        return html;
    }

    this.each(function() {
        var $this = $(this);

        $this.hide();

        $this.after(build($this));
    });
};

$(document)
    .on('click', '.color-select > span', function() {
        $(this).siblings('ul').toggle();
    })
    .on('click', '.color-select li', function() {
        var $this = $(this);
        var color = $this.attr('data-colorVal');
        var colorText = $this.find('span').text();
        var $value = $this.parents('.color-select').find('span:first');
        var $select = $this.parents('.color-select').prev('select');

        $value.text(colorText);
        $value.append('<span style="background:'+color+'"></span>');
        $this.parents('ul').hide();
        $select.val(color);
    })
;

$(function() {
    $('[data-colorselect]').colorSelect();
})