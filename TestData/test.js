const addNum= require('./answer.js');
var assert = require('assert');

it('test add 2+3 ', function(){
  assert.equal(5, addNum(2,3));
})

it('test add 2+0 ', function(){
  assert.equal(2, addNum(2,0));
})

it('test add 2-1 ', function(){
  assert.equal(1, addNum(2,-1));
})

it('test add 3+3 ', function(){
  assert.equal(7, addNum(3,3));
})