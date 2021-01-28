using System;
using Xunit;


     public class AnswerTest{
	
	    Answer answer = new Answer();
	
        [Fact]
        public void testAdd()
        {
	    int expected = 5;
        int actual = answer.add(2,3);

        Assert.Equal(expected, actual);

        }
		
		[Fact]
        public void testAdd1()
        {
	    int expected = 6;
        int actual = answer.add(2,3);

        Assert.Equal(expected, actual);

        }
    }
