import re
import sys

with open(sys.argv[1]) as f:
	lines = f.readlines()

group_names = []
group_datas = []
group_bases = []

key_names_map = {'rotation': 	'rotation.ncrops',
				 'rottime':		'rotation.nyears',
				 'multicrop':	'rotation.multicrop',
				 'firstrotyear':'rotation.firstrotyear',

				 'crop1':		'management[0].pftname',
				 'hydrology1':	'management[0].hydrology',
				 'sdate1':		'management[0].sdate',
				 'hdate1':		'management[0].hdate',
				 'nfert1':		'management[0].nfert',
				 'fallow1':		'management[0].fallow',

				 'crop2':		'management[1].pftname',
				 'hydrology2':	'management[1].hydrology',
				 'sdate2':		'management[1].sdate',
				 'hdate2':		'management[1].hdate',
				 'nfert2':		'management[1].nfert',
				 'fallow2':		'management[1].fallow',

				 'crop3':		'management[2].pftname',
				 'hydrology3':	'management[2].hydrology',
				 'sdate3':		'management[2].sdate',
				 'hdate3':		'management[2].hdate',
				 'nfert3':		'management[2].nfert',
				 'fallow3':		'management[2].fallow',
				 }

for line in lines:
	print "# %s" % line
	if "group" in line or "st " in line:
		group_name = re.search('"(\w+)"', line).group(1)
		group_data = {}
		bases = []
		print "New group: %s" % group_name

	elif line.startswith("\t!") or line.startswith("!"):
		continue

	elif line.startswith("\t"):
		loc_of_comment = line.find('!')
		if loc_of_comment != -1:
			line = line[:loc_of_comment-1]
		spl = line.split()
		if len(spl) == 1:
			print "Reference to another base: %s" % spl[0]
			bases.append(spl[0])
		elif len(spl) >= 2:
			key, value = spl[0].strip(), spl[1].strip()

			# Update key names here to match actual attributes we're setting
			if key in key_names_map.keys():
				key = key_names_map[key]


			if key == "intercrop":
				value = "IntercropType." + value.upper().replace('"', '')
			elif key == "naturalveg":
				value = "NaturalVeg." + value.upper().replace('"', '')
			elif key == "landcover":
				if value == '"cropland"':
					value = "CROP"
				value = "LandcoverType." + value.upper().replace('"', '')
			elif "hydrology" in key:
				value = "HydrologyType."  + value.upper().replace('"', '')
			elif key == "stinclude":
				continue

			group_data[key] = value
			print "Data %s = %s" % (key, value)

	elif ")" in line:
		group_names.append(group_name)
		group_datas.append(group_data)
		group_bases.append(bases)



print "Output below:"
print 
print
print 
for name, data, bases in zip(group_names, group_datas, group_bases):
	o = 'add("%s", ' % (name)

	for base in bases:
		o += '"%s", ' % base

	for key, val in data.iteritems():
		o += '"%s", %s, ' % (key, val)

	o += ")"

	o = o.replace(", )", ");")

	print o
