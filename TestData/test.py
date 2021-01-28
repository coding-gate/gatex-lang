import unittest
from answer import avg
 
class TestUM(unittest.TestCase):
 
    def setUp(self):
        pass
 
    def test_avg_2_4(self):
        self.assertEqual(avg(2,4), 3)
 
    def test_avg_2_3(self):
        self.assertEqual( avg(2,3), 5)
 
if __name__ == '__main__':
    unittest.main()
