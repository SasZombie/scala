#include <iostream>
#include <vector>
#include <algorithm>

std::vector<std::vector<size_t>> get(const std::vector<std::vector<size_t>> & toChange)
{
    std::vector<std::vector<size_t>> toReturn;
    std::vector<size_t> stuff = {2, 3, 7};

    for(size_t i = 0; i < stuff.size(); ++i)
    {
        if(std::find(stuff.begin(), stuff.end(), toChange[0][i]) != stuff.end())
        {
            std::vector<size_t> pusher;
            for(size_t j = 0; j < toChange.size(); ++j)
            {
                pusher.push_back(toChange[j][i]);
            }
            toReturn.push_back(pusher);
        }
    }

    return toReturn;
}

int main()
{
    std::vector<std::vector<std::size_t>> data = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9},
        {10, 11, 12}
    };
    auto getData = get(data);
    for(const auto & v : getData)
    {
        for(const auto& vv : v)
        {
            std::cout << vv << ' ';
        }
        std::cout << '\n';
    }


}